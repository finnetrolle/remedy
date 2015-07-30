package ru.trollsmedjan.remedy.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

/**
 * Created by finnetrolle on 27.07.2015.
 */
@RestController
@RequestMapping("/monitoring")
public class MonitoringResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Response monitoring() {
        log.info("Monitoring method requested");
        return Response.ok().entity(new MonitoringResponse()).build();
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
