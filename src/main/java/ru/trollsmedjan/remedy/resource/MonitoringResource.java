package ru.trollsmedjan.remedy.resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@RestController
@RequestMapping("/monitoring")
public class MonitoringResource {

    @RequestMapping
    public @ResponseBody
    MonitoringResponse monitoring() {
        return new MonitoringResponse();
    }

    public static class MonitoringResponse {
        private String status = "OK";

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
