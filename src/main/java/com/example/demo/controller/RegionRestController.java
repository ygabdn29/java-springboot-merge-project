package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.handler.Utils;
import com.example.demo.model.Region;
import com.example.demo.service.RegionService;

@RestController
@RequestMapping("api")
public class RegionRestController {

// Nilam
//   @Autowired
//   private RegionService regionService;

//   @GetMapping("regions")
//   public ResponseEntity<Object> get(@RequestHeader String tokenAuth) {
//     if (tokenAuth.equals("nilamcantik")) {
//       List<Region> regions = regionService.get();
//       return Utils.generaResponseEntity(HttpStatus.OK, "Data Has Been Retrieved", regions);
//     } else{
//       return Utils.generaResponseEntity(HttpStatus.BAD_REQUEST, "Token Is Not Valid");
//     }
//   }

//   @GetMapping("regions/{id}")
//   public Region getById(@PathVariable Integer id) {
//     Region region = regionService.get(id);
//     return region;
//   }

//   @PostMapping("regions/save")
//   public Boolean saveData(@RequestBody Region region) {
//     return regionService.save(region);
//   }

//   @PostMapping("regions/delete/{id}")
//   public Boolean delete(@PathVariable Integer id) {
//     return regionService.delete(id);
//   }

// Anggia
//     @Autowired
//     private RegionService regionService;

//     @GetMapping("regions")
//     public ResponseEntity<Object> index(@RequestHeader("x-token-key") String token) {
//         if(token.equals("Abcdefgh")){
//             List<Region> regions = regionService.get();
//             return Utils.generateResponseEntity(HttpStatus.OK, "Data has been retrieved", regions);
//         }
//         return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Data can't be access, token is not valid");
//     }

//     @GetMapping("region/{id}")
//     public Region getId(@PathVariable Integer id) {
//         Region region = regionService.get(id);
//         return region;
//     }

//     @PostMapping("region/save")
//     public Boolean saveRegion(@RequestBody Region region) {
//         return regionService.save(region);
//     }

//     @DeleteMapping("region/delete/{id}")
//     public Boolean deleteRegion(@PathVariable Integer id){
//         return regionService.delete(id);
//     }

//   Reza
//     @Autowired
//     private RegionService regionService;

//     @GetMapping("regions")
//     public ResponseEntity<Object> index(@RequestHeader String token) {
//         if(token.equals("test") ){
//             List<Region> regions = regionService.get();
//             return Utils.generateResponseEntity(HttpStatus.OK, "Data has been retrieved", regions);
//         }
//         return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Token is not valid");
//     }

//     @GetMapping("region/{id}")
//     public Region getRegion(@PathVariable Integer id) {
//         Region region = regionService.get(id);
//         return region;
//     }
    
//     @PostMapping("save")
//     public String save(@RequestBody Region region) {
//         return regionService.save(region)? "Data added successfully" : "Failed to add data";
//     }
    
    // @PutMapping("update/{id}")
    // public String update(@PathVariable Integer id, @RequestBody Region newRegion) {
    //     Region region = regionService.get(id);
    //     Boolean result = false;
    //     if(region.getId() == newRegion.getId()){
    //         result = regionService.save(newRegion);
    //     }        
    //     return result? "Data updated successfully" : "Failed to update data";
    // }
    

//     @DeleteMapping("delete/{id}")
//     public String delete(@PathVariable Integer id) {
//         return regionService.delete(id) ? "Data sucessfully deleted" : "Data not found";
//     }
    
// Yoga
//   @Autowired
//   private RegionService regionService;

//   @GetMapping("regions")
//   public ResponseEntity<Object>index(@RequestHeader("x-access-token") String token){
//     if(token.equals("FGH-CVB-BNM")){
//       List<Region> regions = regionService.get();
//       return Utils.generaResponseEntity(HttpStatus.OK, "Retrieved", regions);
//     }
//     // Token ini masuknya bad request
//     return Utils.generaResponseEntity(HttpStatus.BAD_REQUEST, "Token NOT Valid!");
//   }

//   @GetMapping("region/{id}")
//   public Region getById(@PathVariable Integer id){
//     Region region = regionService.get(id);
//     return region;
//   }

//   @PostMapping("region/save")
//   public ResponseEntity<String> saveRegion(@RequestBody Region region){
//     regionService.save(region);
//     return ResponseEntity.ok("Region Saved!");
//   }

  // @PostMapping("region/{id}/update")
  // public String updateRegion(@PathVariable Integer id, @RequestBody Region region){
  //   Region selectedRegion = regionService.get(id);
  //   // updatedRegion.setName(region.getName());
  //   // regionService.save(updatedRegion);
  //   // return ResponseEntity.ok("Region Updated!");
  //   Boolean result = false;
  //   if(selectedRegion.getId() == region.getId()){
  //     result = regionService.save(region);
  //   }
  //   return result ? "Region Updated!" : "Cannot Update";
  // }

//   @DeleteMapping("region/{id}/delete")
//   public String deleteRegion(@PathVariable Integer id){
//     return regionService.delete(id) ? "Region Deleted" : "Region cannot be deleted";
//   }



//   Aten
//     @Autowired
//     private RegionService regionService;

//     @GetMapping("region")
//     public ResponseEntity<Object> getRegionsList(@RequestHeader String X_Token){
//         if(X_Token.equals("Test Token") ){
//             List<Region> regions = regionService.get();
//             return Utils.generateResponseEntity(HttpStatus.OK, "Data Berhasil di Ambil", regions);
//         }
        
//         return Utils.generateResponseEntity(HttpStatus.FORBIDDEN, X_Token, X_Token);
//     }

//       @GetMapping("getIDs")
//     public List<Integer> getRegionIDs() {
//         List<Region> regionsList = regionService.get();
//         List<Integer> ids = new ArrayList<>();
//         for (Region region : regionsList) {
//             ids.add(region.getId());
//         }
//         return ids;
//     }

//     @PostMapping("save")
//     public Boolean save(@RequestBody Region region){
//         return regionService.save(region);
//     }

//     @PostMapping("delete")
//     public Boolean delete(@RequestBody Region region) {
//         Integer regionId = region.getId();
//         return regionService.delete(regionId);
//     }

//     @PutMapping("update")
//     public boolean update(@RequestBody Region region) {
//         Integer regionID = region.getId();
//         if (regionService.get(regionID) != null) {
//             return regionService.save(region);
//         }
//         return false; 
//     }


}
