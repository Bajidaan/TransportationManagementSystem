package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.RideRequestDto;
import com.ingridprojectsix.transportation_management_system.dto.RideRequestUpdate;
import com.ingridprojectsix.transportation_management_system.exception.DriverNotFoundException;
import com.ingridprojectsix.transportation_management_system.exception.RideRequestNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.*;
import com.ingridprojectsix.transportation_management_system.model.domain.RequestStatus;
import com.ingridprojectsix.transportation_management_system.model.domain.RideRequestStatus;
import com.ingridprojectsix.transportation_management_system.repository.*;
import com.ingridprojectsix.transportation_management_system.utils.MessageResponse;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideRequestService {
    private final RideRequestRepository requestRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final DriverStatusRepository driverStatusRepository;
    private final RidesRepository ridesRepository;
    private final EmailService emailService;

    private static final String KEY = "4d182eb0a92745f398a544127462b36b";
    private static final double EARTH_RADIUS = 6371;
    private static final double COST_PER_kM = 200;
    private static final String SUPPORT_CONTACT = "+234675895490";

    public List<RideRequest> getAllRideRequest() {
        return requestRepository.findAll();
    }

    public RideRequest getRideRequest(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(RideRequestNotFoundException::new);
    }

    public RideRequest getRideRequestByPassengerId(Long passengerId) {
       return requestRepository.findByPassenger_PassengerId(passengerId)
               .orElseThrow(RideRequestNotFoundException::new);
    }

    public Map<String, String> saveRideRequest(RideRequestDto request) throws MessagingException {
        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow();

        List<DriverStatus> driverList = driverStatusRepository.findAll();

        Driver assignedDriver = assignDriver(driverList, getCoordinate(request.getStartLocation()));

        DriverStatus driverStatus = null;

        if (assignedDriver != null) {
            driverStatus = driverStatusRepository.findByDriver_DriverId(assignedDriver.getDriverId())
                    .orElseThrow(DriverNotFoundException::new);
        }

       RideRequest rideRequest = new RideRequest(request);

        if (canOderRide(request, passenger)) {
            if (assignedDriver == null) {
                rideRequest.setStatus(RideRequestStatus.NO_RIDE);
                requestRepository.save(rideRequest);

                emailService.sendNoRideAvailableEmail(passenger.getEmail(), passenger.getFirstName(),
                        request.getStartLocation(), request.getEndLocation(), SUPPORT_CONTACT);

                return MessageResponse.displayMessage("No ride");
            }

            rideRequest.setPassenger(passenger);
            rideRequest.setCurrentAssignedDriver(assignedDriver);
            rideRequest.setStatus(RideRequestStatus.PENDING);
            requestRepository.save(rideRequest);

            //send email to the passenger
            emailService.sendRideRequestPendingEmail(passenger.getEmail(), passenger.getFirstName(),
                    request.getStartLocation(), request.getEndLocation(),
                    rideRequest.getTime().toString(), SUPPORT_CONTACT);

            // send email to the Driver
            emailService.sendRideRequestEmail(assignedDriver.getEmail(), assignedDriver.getLastName(),
                    passenger.getFirstName(), request.getStartLocation(), request.getEndLocation(),
                    rideRequest.getTime().toString(), SUPPORT_CONTACT);

            return MessageResponse.displayMessage("successfully request for ride. Driver will be assign shortly");
        }
        return MessageResponse.displayMessage("unable to order. Recharge your account");
    }

    public Map<String, String> updateRequestByPassenger(Long requestId, RideRequestUpdate request) {
        RideRequest toUpdate = requestRepository.findById(requestId)
                .orElseThrow(RideRequestNotFoundException::new);

        toUpdate.setStartLocation(request.getStartLocation());
        toUpdate.setEndLocation(request.getEndLocation());

        requestRepository.save(toUpdate);

        return MessageResponse.displayMessage("update successfully");
    }

    public Map<String, String> driverResponses(Long requestId, boolean status) throws MessagingException {
        List<DriverStatus> driverList = driverStatusRepository.findAll();

        RideRequest request = requestRepository.findById(requestId)
                .orElseThrow(RideRequestNotFoundException::new);

        Driver assignedDriver = request.getCurrentAssignedDriver();;

        DriverStatus driverStatus = driverStatusRepository.findByDriver_DriverId(assignedDriver.getDriverId())
                .orElseThrow(DriverNotFoundException::new);

        double distance = calculateDistance(getCoordinate(request.getStartLocation()),
                getCoordinate(request.getEndLocation()));

        if (status) {
            request.setStatus(RideRequestStatus.ACCEPTED);

            ridesRepository.save(convertToRides(request,
                    distance * COST_PER_kM, assignedDriver));

            requestRepository.save(request);

            emailService.sendRideAssignedEmail(request.getPassenger().getEmail(),
                    request.getPassenger().getFirstName(), request.getStartLocation(), request.getEndLocation(),
                    assignedDriver.getFirstName(), assignedDriver.getLicenseNumber(),
                    "noon", SUPPORT_CONTACT);

            return MessageResponse.displayMessage("A driver as been assign");
        }

        driverStatus.setLastRejectionTime(LocalDateTime.now());
        driverStatusRepository.save(driverStatus);

        Driver newAssignedDriver = assignDriver(driverList, getCoordinate(request.getStartLocation()));

        if (newAssignedDriver == null) {
            request.setStatus(RideRequestStatus.NO_RIDE);
            requestRepository.save(request);

            emailService.sendNoRideAvailableEmail(request.getPassenger().getEmail(),
                    request.getPassenger().getFirstName(),
                    request.getStartLocation(), request.getEndLocation(), SUPPORT_CONTACT);

            return MessageResponse.displayMessage("No ride");
        }

        request.setCurrentAssignedDriver(newAssignedDriver);

        // send email to the Driver
        emailService.sendRideRequestEmail(assignedDriver.getEmail(),  newAssignedDriver.getLastName(),
                request.getPassenger().getFirstName(), request.getStartLocation(), request.getEndLocation(),
                request.getTime().toString(), SUPPORT_CONTACT);

        requestRepository.save(request);

        return MessageResponse.displayMessage("Ride rejected, Another driver will be assigned");
    }

    private Rides convertToRides(RideRequest request, double fare, Driver driver) {
        Rides rides = new Rides();

        rides.setPassengers(request.getPassenger());
        rides.setStartLocation(request.getStartLocation());
        rides.setEndLocation(request.getEndLocation());
        rides.setFare(fare);
        rides.setStatus(RequestStatus.PENDING);
        rides.setStartTime(null);
        rides.setEndTime(null);
        rides.setDrivers(driver);

        return rides;
    }

    private boolean canOderRide(RideRequestDto request, Passenger passenger) {

        double distance = calculateDistance(getCoordinate(request.getStartLocation()),
                getCoordinate(request.getEndLocation()));

        double costOfRide = distance * COST_PER_kM;
        log.info("Distance {}", distance);
        log.info("cost of ride {}", costOfRide);
        log.info("passenger account {}", passenger.getAccountBalance());

        return (costOfRide < passenger.getAccountBalance());
    }

    private Driver assignDriver(List<DriverStatus> drivers, double[] passengerCoordinate) {
        double minDistance = 5000;
        DriverStatus assignDriver = null;

        for (DriverStatus driver : drivers) {
            double[] driverLocation = {driver.getLatitude(), driver.getLongitude()};

            double distance = calculateDistance(passengerCoordinate, driverLocation);


            if (driver.isAvailability() && hasCoolDownExpired(driver)) {
                if (distance < minDistance) {
                    minDistance = distance;
                    assignDriver = driver;
                }
            }
        }

        if (assignDriver == null) {
            return null;
        }

        return driverRepository.findById(assignDriver.getDriver()
                    .getDriverId()).orElseThrow(DriverNotFoundException::new);
    }


    private double[] getCoordinate(String address) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
        request.setRestrictToCountryCode("ng"); // restrict results to Nigeria
        request.setBounds(3.331, 6.393, 3.606, 6.696); // restrict results to a geographic bounding box (southWestLng, southWestLat, northEastLng, northEastLat)

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

        log.info("Coordinate {} {}", firstResultLatLng.getLat(), firstResultLatLng.getLng());
        return new double[]{firstResultLatLng.getLat(), firstResultLatLng.getLng()};
    }

    private double calculateDistance(double[] start, double[] end) {
        // Convert latitude and longitude from degrees to radians
        double startLatRad = Math.toRadians(start[0]);
        double endLatRad = Math.toRadians(start[1]);
        double deltaLatRad = Math.toRadians(end[0] - start[0]);
        double deltaLonRad = Math.toRadians(end[1] - start[1]);

        // Haversine formula
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                Math.cos(startLatRad) * Math.cos(endLatRad) *
                        Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private boolean hasCoolDownExpired(DriverStatus driverStatus) {
        // cool down period is 30 minutes
        Duration coolDownPeriod = Duration.ofMinutes(30);

        if (driverStatus.getLastRejectionTime() == null) {
            // Driver hasn't rejected a ride before
            return true;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime rejectionTime = driverStatus.getLastRejectionTime();

        return Duration.between(rejectionTime, currentTime).compareTo(coolDownPeriod) >= 0;
    }
}