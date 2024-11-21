package com.example.esd_hostel_service.controller;


import com.example.esd_hostel_service.model.Hostel;
import com.example.esd_hostel_service.model.UserDto;
import com.example.esd_hostel_service.service.HostelService;
import com.example.esd_hostel_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/hostel")
public class HostelController {


    @Autowired
    private HostelService hostelService;
    @Autowired
    private UserService userService;





    @GetMapping("allrooms")
    public ResponseEntity<List<List<List<Hostel>>>> getAllHostelsRooms(@RequestParam String name,@RequestParam int floor,@RequestParam int all,@RequestHeader("Authorization") String jwt) throws Exception{

        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userService.getUserProfileHandler(jwt);

        if(user==null || !user.getRole().equals("ROLE_WARDEN")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        List<Hostel> hostels=null;
        try{
            hostels=hostelService.getAllHostelsRooms(name,floor,all);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(hostels.isEmpty()) {
            List<List<List<Hostel>>> t=new ArrayList<>();
            return ResponseEntity.ok(t);
        }

        HashMap<String, HashMap<Integer,List<Hostel>>> hashMap = new  HashMap<>();
        for(Hostel hostel:hostels){
            if(hashMap.containsKey(hostel.getName())){
                if(hashMap.get(hostel.getName()).containsKey(hostel.getFloor())){
                    hashMap.get(hostel.getName()).get(hostel.getFloor()).add(hostel);
                }else{
                    List<Hostel> temp = new ArrayList<>();
                    temp.add(hostel);
                    hashMap.get(hostel.getName()).put(hostel.getFloor(), temp);
                }
            }else{
                HashMap<Integer,List<Hostel>> temp = new HashMap<>();
                List<Hostel> temp1 = new ArrayList<>();
                temp1.add(hostel);
                temp.put(hostel.getFloor(), temp1);
                hashMap.put(hostel.getName(), temp);
            }
        }

//        for(Map.Entry<String, HashMap<Integer, List<Hostel>>> entry:hashMap.entrySet()){
//            System.out.print(entry.getKey() + "->");
//            for(Map.Entry<Integer,List<Hostel>> entry1:entry.getValue().entrySet()){
//                System.out.println(entry1.getKey() + "->" + entry1.getValue());
//            }
//        }

        Collection<HashMap<Integer,List<Hostel>>> values = hashMap.values();

        // Creating an ArrayList of values
        ArrayList<HashMap<Integer,List<Hostel>>> listOfValues = new ArrayList<>(values);

        List<List<List<Hostel>>> val=new ArrayList<>();

        for(HashMap<Integer,List<Hostel>> map:listOfValues){
            Collection<List<Hostel>>final_pro=map.values();
            List<List<Hostel>> temp=new ArrayList<>(final_pro);
            val.add(temp);
        }
        return ResponseEntity.ok(val);



    }

    @GetMapping("room/{id}")
    public ResponseEntity<Hostel> getHostelsRoomsById(@PathVariable int id,@RequestHeader("Authorization") String jwt) throws Exception{

        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userService.getUserProfileHandler(jwt);

        if(user==null || !user.getRole().equals("ROLE_WARDEN")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Hostel hostel=null;
        try{
            hostel=hostelService.getHostelsRoomsById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(hostel);
    }




    @PostMapping("add")
    public ResponseEntity<List<Hostel>> add_rooms(@RequestBody List<Hostel> hostels,@RequestHeader("Authorization") String jwt) throws Exception {
        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userService.getUserProfileHandler(jwt);

        if(user==null || !user.getRole().equals("ROLE_WARDEN")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            hostelService.add_rooms(hostels);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.internalServerError().body(hostels);
        }


       hostels=null;

        try{
            hostels=hostelService.getAllHostelsRooms("",0,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return hostels.size()>0?ResponseEntity.ok(hostels):ResponseEntity.noContent().build();
    }

    @PutMapping("allocate")
    public ResponseEntity<Hostel> allocate_rooms(@RequestBody Hostel hostel,@RequestHeader("Authorization") String jwt) throws Exception {

        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userService.getUserProfileHandler(jwt);

        if(user==null || !user.getRole().equals("ROLE_WARDEN")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        System.out.println(hostel);
        try{
            hostel=hostelService.allocate_rooms(hostel);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hostel);
        }

        return ResponseEntity.ok(hostel);
    }

    @PutMapping("vacant/{id}")
    public ResponseEntity<Hostel> vacant_room(@PathVariable int id,@RequestBody Hostel hostel,@RequestHeader("Authorization") String jwt) throws Exception {
        if(jwt==null){
            throw new Exception("jwt required...");
        }
        UserDto user=userService.getUserProfileHandler(jwt);

        if(user==null || !user.getRole().equals("ROLE_WARDEN")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            hostel=hostelService.vacant_room(hostel);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseEntity.internalServerError().body(hostel);
        }
        return ResponseEntity.ok(hostel);
    }



}
