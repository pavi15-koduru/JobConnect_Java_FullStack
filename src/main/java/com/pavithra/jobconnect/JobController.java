package com.pavithra.jobconnect;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JobController {
  private final JobService service;
  public JobController(JobService service) { this.service = service; }

  @GetMapping("/jobs")
  public List<Job> getJobs(@RequestParam(required=false) String search) { return service.findJobs(search); }

  @PostMapping("/applications")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String,String> apply(@RequestBody ApplicationRequest request) {
    if (request.applicantName() == null || request.applicantName().isBlank() ||
        request.email() == null || request.email().isBlank())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and email are required");
    if (!request.email().contains("@") || !request.email().contains("."))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enter a valid email address");
    service.apply(request.jobId(), request.applicantName().trim(), request.email().trim());
    return Map.of("message", "Application submitted successfully");
  }

  @GetMapping("/applications")
  public List<JobApplication> getApplications() { return service.findApplications(); }

  public record ApplicationRequest(long jobId, String applicantName, String email) {}
}
