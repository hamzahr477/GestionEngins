package com.marsamaroc.gestionengins;

import com.marsamaroc.gestionengins.entity.Shift;
import com.marsamaroc.gestionengins.repository.CritereRepository;
import com.marsamaroc.gestionengins.repository.ShiftRepository;
import com.marsamaroc.gestionengins.service.DemandeService;
import com.marsamaroc.gestionengins.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalTime;

@SpringBootApplication
public class GestionenginsApplication implements CommandLineRunner {

    @Autowired
    ShiftService shiftService;

    public static void main(String[] args) {
        SpringApplication.run(GestionenginsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUN");
        Shift shift1 = new Shift();
        Shift shift2 = new Shift();
        Shift shift3 = new Shift();
        shift1.setCodeShift("S1");
        shift1.setHeureDebut(LocalTime.of(0,0));
        shift1.setHeureFin(LocalTime.of(7,59,59));
        shift2.setCodeShift("S2");
        shift2.setHeureDebut(LocalTime.of(8,0));
        shift2.setHeureFin(LocalTime.of(15,59,59));
        shift3.setCodeShift("S3");
        shift3.setHeureDebut(LocalTime.of(16,0));
        shift3.setHeureFin(LocalTime.of(23,59,59));

        shiftService.saveIfNotExist(shift1);
        shiftService.saveIfNotExist(shift2);
        shiftService.saveIfNotExist(shift3);
    }
}
