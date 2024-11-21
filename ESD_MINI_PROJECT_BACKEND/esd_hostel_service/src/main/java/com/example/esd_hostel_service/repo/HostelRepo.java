package com.example.esd_hostel_service.repo;

import com.example.esd_hostel_service.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HostelRepo extends JpaRepository<Hostel, Integer> {

}
