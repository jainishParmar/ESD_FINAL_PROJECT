package com.example.esd_hostel_service.service;


import com.example.esd_hostel_service.model.Hostel;
import com.example.esd_hostel_service.repo.HostelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelService {

    @Autowired
    private HostelRepo hostelRepo;

    public  Hostel getHostelsRoomsById(int id) throws Exception {
         return hostelRepo.findById(id).orElse(null);
    }


    public List<Hostel> getAllHostelsRooms(String name,int floor,int all) throws  Exception{

        List<Hostel> hostels = null;
        List<Hostel>Filter_List_by_name=new ArrayList<>();
        List<Hostel>Filter_List_by_floor=new ArrayList<>();
        List<Hostel>Filter_List_by_all=new ArrayList<>();
        try{
            hostels = hostelRepo.findAll();
        } catch (Exception e) {
            throw new Exception("error while getting hostel data");
        }

        if(name.length()==0 && floor==0 && all==0){
            return hostels;
        }

        if(name.length()>0){
            Filter_List_by_name=hostels.stream().filter(x -> x.getName().equals(name)).toList();
        }

        if(floor >0){
            if(name.length()>0){
                if(Filter_List_by_name.size()>0){
                    Filter_List_by_floor=Filter_List_by_name.stream().filter(x -> x.getFloor()==floor).toList();
                }
            }else{
                Filter_List_by_floor=hostels.stream().filter(x -> x.getFloor()==floor).toList();

            }

        }

        if(all>0){

            if(all==1){

                if(name.length()>0 && floor>0) {
                    Filter_List_by_all=Filter_List_by_floor.stream().filter(x -> x.getStudent()==null).toList();
                }else if(floor > 0){
                    Filter_List_by_all=Filter_List_by_floor.stream().filter(x -> x.getStudent()==null).toList();

                }else if(name.length()>0) {
                    Filter_List_by_all=Filter_List_by_name.stream().filter(x -> x.getStudent()==null).toList();
                }else{
                    Filter_List_by_all=hostels.stream().filter(x -> x.getStudent()==null).toList();
                }

            }

            if(all==2){
                if(name.length()>0 && floor>0) {
                    Filter_List_by_all=Filter_List_by_floor.stream().filter(x -> x.getStudent()!=null).toList();
                }else if(floor > 0){
                    Filter_List_by_all=Filter_List_by_floor.stream().filter(x -> x.getStudent()!=null).toList();

                }else if(name.length()>0){
                    Filter_List_by_all=Filter_List_by_name.stream().filter(x -> x.getStudent()!=null).toList();
                }else{
                    Filter_List_by_all=hostels.stream().filter(x -> x.getStudent()!=null).toList();

                }
            }

        }



        if(all!=0){
            return Filter_List_by_all;
        }else if(floor!=0){
            return Filter_List_by_floor;
        }
        return Filter_List_by_name;






    }

    public void add_rooms(List<Hostel> hostels) throws  Exception{

        try{
            hostelRepo.saveAll(hostels);
        }catch (Exception e) {
            throw new Exception("error while storing rooms");
        }

    }

    public Hostel allocate_rooms(Hostel hostel) throws  Exception{

        try {
            return hostelRepo.save(hostel);
        }
        catch (Exception e) {
            throw new Exception("error while allocating rooms");
        }
    }

    public Hostel vacant_room(Hostel hostel) throws  Exception {
        hostel.setStudent(null);
        try {
            return hostelRepo.save(hostel);
        }
        catch (Exception e) {
            throw new Exception("error while vacating rooms");
        }
    }
}
