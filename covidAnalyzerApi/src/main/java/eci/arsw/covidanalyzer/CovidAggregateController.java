package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.exceptions.CovidAggregateException;
import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CovidAggregateController {
    @Autowired
    ICovidAggregateService covidAggregateService;

    //Posts

    @PostMapping("/covid/result/true-positive")
    public ResponseEntity<?> addTruePositiveResult(@RequestBody Result result) {
        //TODO
        try {
            return new ResponseEntity<>(covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE), HttpStatus.CREATED);
        } catch (CovidAggregateException e) {
            return new ResponseEntity<>("Este Resultado ya existe.",HttpStatus.FORBIDDEN);
        }

    }
    @PostMapping("/covid/result/true-negative")
    public ResponseEntity<?> addTrueNegativeResult(@RequestBody Result result) {
        //TODO
        try {
            return new ResponseEntity<>(covidAggregateService.aggregateResult(result, ResultType.TRUE_NEGATIVE), HttpStatus.CREATED);
        } catch (CovidAggregateException e) {
            return new ResponseEntity<>("Este Resultado ya existe.",HttpStatus.FORBIDDEN);
        }

    }
    @PostMapping("/covid/result/false-positive")
    public ResponseEntity<?> addFalsePositiveResult(@RequestBody Result result) {
        //TODO
        try {
            return new ResponseEntity<>(covidAggregateService.aggregateResult(result, ResultType.FALSE_POSITIVE), HttpStatus.CREATED);
        } catch (CovidAggregateException e) {
            return new ResponseEntity<>("Este Resultado ya existe.",HttpStatus.FORBIDDEN);
        }

    }
    @PostMapping("/covid/result/false-negative")
    public ResponseEntity<?> addFalseNegativeResult(@RequestBody Result result) {
        //TODO
        try {
            return new ResponseEntity<>(covidAggregateService.aggregateResult(result, ResultType.FALSE_NEGATIVE), HttpStatus.CREATED);
        } catch (CovidAggregateException e) {
            return new ResponseEntity<>("Este Resultado ya existe.",HttpStatus.FORBIDDEN);
        }

    }


    //Gets

    @GetMapping("/covid/result/true-positive")
    public ResponseEntity<?> getTruePositiveResult() {
        //TODO

        return new ResponseEntity<>(covidAggregateService.getResult(ResultType.TRUE_POSITIVE),HttpStatus.OK);
    }
    @GetMapping("/covid/result/true-negative")
    public ResponseEntity<?> getTrueNegativeResult() {
        //TODO
        return new ResponseEntity<>(covidAggregateService.getResult(ResultType.TRUE_NEGATIVE),HttpStatus.OK);
    }
    @GetMapping("/covid/result/false-positive")
    public ResponseEntity<?> getFalsePositiveResult() {
        //TODO
        return new ResponseEntity<>(covidAggregateService.getResult(ResultType.FALSE_POSITIVE),HttpStatus.OK);
    }
    @GetMapping("/covid/result/false-negative")
    public ResponseEntity<?> getFalseNegativeResult() {
        //TODO
        return new ResponseEntity<>(covidAggregateService.getResult(ResultType.FALSE_NEGATIVE),HttpStatus.OK);
    }


    //PUT

    @PutMapping("/covid/result/persona/{uuid}")
    public ResponseEntity<?> savePersonaWithMultipleTests(@PathVariable UUID uuid, @RequestBody ResultType result) {
        //TODO
        covidAggregateService.upsertPersonWithMultipleTests(uuid,result);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //BONO NUMERO 1
    @GetMapping("/covid/result/{date}")
    public ResponseEntity<?> getTrueNegativeResult(@PathVariable String date) {
        //TODO
        return new ResponseEntity<>(covidAggregateService.getResultByDate(date),HttpStatus.OK);
    }

    
}