package com.medilink.api.services;

import com.medilink.api.models.Queue;

import java.util.List;

public interface QueueService {
    Queue addToQueue(Queue queue);
    Queue updateQueuePosition(String queueId, int position);
    void deleteFromQueue(String queueId);
    Queue getQueueById(String queueId);
    List<Queue> getAllQueues();
}
