package com.medilink.api.controllers;

import com.medilink.api.models.Queue;
import com.medilink.api.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queues")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping
    public ResponseEntity<Queue> addToQueue(@RequestBody Queue queue) {
        Queue addedQueue = queueService.addToQueue(queue);
        return new ResponseEntity<>(addedQueue, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Queue> updateQueuePosition(@PathVariable String id, @RequestParam int position) {
        Queue updatedQueue = queueService.updateQueuePosition(id, position);
        return new ResponseEntity<>(updatedQueue, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFromQueue(@PathVariable String id) {
        queueService.deleteFromQueue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Queue> getQueueById(@PathVariable String id) {
        Queue queue = queueService.getQueueById(id);
        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Queue>> getAllQueues() {
        List<Queue> queues = queueService.getAllQueues();
        return new ResponseEntity<>(queues, HttpStatus.OK);
    }
}
