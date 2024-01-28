package com.ingridprojectsix.transportation_management_system.service;

import com.ingridprojectsix.transportation_management_system.exception.RideRequestNotFoundException;
import com.ingridprojectsix.transportation_management_system.model.DriverStatus;
import com.ingridprojectsix.transportation_management_system.model.Passenger;
import com.ingridprojectsix.transportation_management_system.model.RideRequest;
import com.ingridprojectsix.transportation_management_system.repository.PassengerRepository;
import com.ingridprojectsix.transportation_management_system.repository.RideRequestRepository;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RideRequestService {
    private final RideRequestRepository requestRepository;
    private final PassengerRepository passengerRepository;
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

    public Map<String, String> updateRequest(Long requestId, RideRequest request) {
        RideRequest toUpdate = requestRepository.findById(requestId)
                .orElseThrow(RideRequestNotFoundException::new);

        request.setRequestId(toUpdate.getRequestId());

        requestRepository.save(request);

        return Map.of("message", "update successfully");
    }

    public boolean canOderRide(Long passengerId, RideRequest request) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow();

        double distance = calculateDistance(getCoordinate(request.getStartLocation()),
                getCoordinate(request.getEndLocation()));

        double costOfRide = distance * COST_PER_kM;

        //return new ResponseEntity<>(Map.of("message", "Insufficient account balance"), HttpStatus.BAD_REQUEST);
        return (costOfRide < passenger.getAccountBalance());
    }

    public DriverStatus assignDriver(List<DriverStatus> drivers, double[] passengerCoordinate) {
        double minDistance = 5000;
        DriverStatus assignDriver = null;

        for (DriverStatus driver : drivers) {
            double[] driverLocation = {driver.getLatitude(), driver.getLongitude()};

            double distance = calculateDistance(passengerCoordinate, driverLocation);


            if (driver.isAvailable() && distance < minDistance) {
                minDistance = distance;
                assignDriver = driver;
            }
        }
        return assignDriver;
    }


    private double[] getCoordinate(String address) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
        request.setRestrictToCountryCode("ng"); // restrict results to Nigeria
        request.setBounds(3.331, 6.393, 3.606, 6.696); // restrict results to a geographic bounding box (southWestLng, southWestLat, northEastLng, northEastLat)

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

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
