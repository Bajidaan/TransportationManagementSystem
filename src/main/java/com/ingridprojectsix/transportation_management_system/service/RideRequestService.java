package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.dto.RideRequestDto;
import com.ingridprojectsix.transportation_management_system.exception.DriverNotFoundException;
import com.ingridprojectsix.transportation_management_system.exception.RideRequestNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.*;
import com.ingridprojectsix.transportation_management_system.repository.*;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private static final String KEY = "4d182eb0a92745f398a544127462b36b";
    private static final double EARTH_RADIUS = 6371;
    private static final double COST_PER_kM = 200;


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

    public Map<String, String> saveRideRequest(RideRequestDto request) {
        Passenger passenger = passengerRepository.findById(request.getPassengerId())
                .orElseThrow();

       RideRequest rideRequest = new RideRequest(request);
       rideRequest.setPassenger(passenger);

        if (canOderRide(request, passenger)) {
            requestRepository.save(rideRequest);
            return Map.of("message", "successfully request for ride. Driver will be assign shortly");
        }
        return Map.of("message", "unable to order. Load your account");
    }

    public Map<String, String> updateRequest(Long requestId, RideRequest request) {
        RideRequest toUpdate = requestRepository.findById(requestId)
                .orElseThrow(RideRequestNotFoundException::new);

        request.setRequestId(toUpdate.getRequestId());

        requestRepository.save(request);

        return Map.of("message", "update successfully");
    }

    public Map<String, String> updateStatus(Long requestId) {
        RideRequest request = requestRepository.findById(requestId)
                .orElseThrow(RideRequestNotFoundException::new);

        List<DriverStatus> drivers = driverStatusRepository.findAll();
        Driver assignedDriver = assignDriver(drivers, getCoordinate(request.getStartLocation()));

        double distance = calculateDistance(getCoordinate(request.getStartLocation()),
                getCoordinate(request.getEndLocation()));

        if (assignedDriver == null) {
            request.setStatus(RideRequestStatus.NO_RIDE);
            requestRepository.save(request);
            return Map.of("message", "No ride currently");
        }

        request.setStatus(RideRequestStatus.ACCEPTED);
        requestRepository.save(request);
        ridesRepository.save(convertToRides(request,
                    distance * COST_PER_kM, assignedDriver));

        return Map.of("message", "A driver as been assign");
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

    public Driver assignDriver(List<DriverStatus> drivers, double[] passengerCoordinate) {
        double minDistance = 5000;
        DriverStatus assignDriver = null;

        for (DriverStatus driver : drivers) {
            double[] driverLocation = {driver.getLatitude(), driver.getLongitude()};

            double distance = calculateDistance(passengerCoordinate, driverLocation);


            if (driver.isAvailability() && distance < minDistance) {
                minDistance = distance;
                assignDriver = driver;
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
}
