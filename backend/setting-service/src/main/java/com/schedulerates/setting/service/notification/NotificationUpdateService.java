package com.schedulerates.setting.service.notification;

/**
 * Service interface named {@link NotificationUpdateService} for updating notifications.
 */
public interface NotificationUpdateService {

    /**
     * Updates a FAQ identified by its unique ID using the provided update request.
     *
     * @param faqId           The ID of the FAQ to update.
     * @param faqUpdateRequest The request containing updated data for the FAQ.
     * @return The updated FAQ object.
     */
    void updateNotificationStatus();

}