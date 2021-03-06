package org.teamwe.carrent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.teamwe.carrent.controller.utils.FileUtil;
import org.teamwe.carrent.controller.utils.Format;
import org.teamwe.carrent.controller.utils.ParamValidate;
import org.teamwe.carrent.service.CarService;
import org.teamwe.carrent.utils.ReturnStatus;
import org.teamwe.carrent.utils.StringUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author FDws
 * Created on 2018/6/17 20:25
 */

@RestController
public class CarController {
    private final CarService service;
    private final FileUtil fu;

    @Autowired
    public CarController(CarService service, FileUtil fu) {
        this.service = service;
        this.fu = fu;
    }

    @GetMapping("/images/{card}")
    public Format getCarImages(@PathVariable String card) {
        return new Format().code(ReturnStatus.SUCCESS).
                addData("images", Arrays.asList(service.get_car_imgs(card)));
    }

    @GetMapping("/type")
    public Format getTypes() {
        return new Format().code(ReturnStatus.SUCCESS).addData("types", service.getType());
    }

    @PostMapping("/type")
    public Format addType(@RequestParam Integer number) {
        int res = service.addType(number);
        return new Format().code(res);
    }

    @GetMapping("/brand")
    public Format getBrands() {
        return new Format().code(ReturnStatus.SUCCESS).addData("brands", service.getBrand());
    }

    @PostMapping("/brand")
    public Format addBrand(@RequestParam String name, @RequestParam MultipartFile file) {
        if (!new ParamValidate(null).file(file, fu.getMaxFileSize(), true).validate()) {
            return new Format().code(ReturnStatus.FAILURE);
        }
        String fn = fu.saveImage(file);
        return new Format().code(service.addBrand(name, fn));
    }

    @PostMapping("/car")
    public Format addCar(@RequestParam String email,
                         @RequestParam int type,
                         @RequestParam String card,
                         @RequestParam String brand,
                         @RequestParam("file") MultipartFile[] files,
                         @RequestParam String message,
                         @RequestParam int price,
                         @RequestParam String city) {

        if (!StringUtil.isLegalMail(email.trim())) {
            return new Format().code(ReturnStatus.FAILURE).message(StringUtil.ILLEGAL_EMAIL);
        }
        if (files.length > 4 || files.length < 1) {
            return new Format().code(ReturnStatus.FAILURE).message("Car's Picture number " + files.length + " is Illegal");
        }
        List<String> images = new LinkedList<>();
        for (MultipartFile file : files) {
            if (new ParamValidate(null).file(file, fu.getMaxFileSize(), true).validate()) {
                images.add(fu.saveImage(file));
            }
        }
        String[] im = new String[images.size()];
        for (int i = 0; i < im.length; i++) {
            im[i] = images.get(i);
        }

        return new Format().code(service.addCar(email.trim(),
                type, card.trim(), brand.trim(), im, message, price, city));
    }

    @GetMapping("/checkcar")
    public Format getCheckCar() {
        return new Format().code(ReturnStatus.SUCCESS).addData("cars", service.getCheckCar());
    }

    @PutMapping("/checkcar")
    public Format putCheckCar(@RequestParam String card) {
        return new Format().code(service.checkCar(card.trim()));
    }

    @GetMapping("/car")
    public Format getCars(@RequestParam int begin,
                          @RequestParam int length,
                          @RequestParam int type,
                          @RequestParam String city,
                          @RequestParam String brand) {

        return new Format().code(ReturnStatus.SUCCESS)
                .addData("cars", service.getCars(begin, length, type, brand.trim(), city));
    }

    @GetMapping("/car/pages")
    public Format getPages(@RequestParam int type,
                           @RequestParam String brand,
                           @RequestParam int length,
                           @RequestParam String city) {
        return new Format().code(ReturnStatus.SUCCESS).
                addData("page", service.carPages(type, brand, length, city));
    }

    @GetMapping("/car/{card}")
    public Format getCar(@PathVariable String card){
        return new Format().code(ReturnStatus.SUCCESS).addData("car",service.getCar(card));

    }
}
