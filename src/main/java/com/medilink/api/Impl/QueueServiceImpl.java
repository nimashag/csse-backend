package com.medilink.api.Impl;

import com.medilink.api.models.Queue;
import com.medilink.api.repositories.QueueRepository;
import com.medilink.api.services.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    private QueueRepository queueRepository;

    @Override
    public Queue addToQueue(Queue queue) {
        return queueRepository.save(queue);
    }

    @Override
    public Queue updateQueuePosition(String queueId, int position) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));
        queue.setPosition(position);
        return queueRepository.save(queue);
    }

    @Override
    public void deleteFromQueue(String queueId) {
        queueRepository.deleteById(queueId);
    }

    @Override
    public Queue getQueueById(String queueId) {
        return queueRepository.findById(queueId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));
    }

    @Override
    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }
}
