package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Api(basePath = "/monitoring", value = "Monitoring", description = "monitoring!", produces = "application/json")
@RestController
@RequestMapping("/monitoring")
public class MonitoringResource {

    @RequestMapping(method = RequestMethod.GET)
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
