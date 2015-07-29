package ru.trollsmedjan.remedy.resource;

import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@Api(basePath = "/monitoring", value = "Monitoring", description = "monitoring!", produces = "application/json")
@RestController
@RequestMapping("/monitoring")
public class MonitoringResource {

    private static final Logger log = Logger.getLogger(MonitoringResource.class);

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    MonitoringResponse monitoring() {
        log.info("Monitoring method requested");
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
