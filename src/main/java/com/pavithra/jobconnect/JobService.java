package com.pavithra.jobconnect;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class JobService {
  private final JdbcTemplate jdbc;
  public JobService(JdbcTemplate jdbc) { this.jdbc = jdbc; }

  public List<Job> findJobs(String search) {
    String sql = "SELECT id,title,company,location,skills,description FROM jobs";
    if (search == null || search.isBlank()) return jdbc.query(sql + " ORDER BY id", (rs,n) ->
      new Job(rs.getLong("id"),rs.getString("title"),rs.getString("company"),rs.getString("location"),rs.getString("skills"),rs.getString("description")));
    String term = "%" + search.trim() + "%";
    return jdbc.query(sql + " WHERE title LIKE ? OR company LIKE ? OR skills LIKE ? ORDER BY id",
      (rs,n) -> new Job(rs.getLong("id"),rs.getString("title"),rs.getString("company"),rs.getString("location"),rs.getString("skills"),rs.getString("description")),
      term,term,term);
  }

  public void apply(long jobId, String name, String email) {
    Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM jobs WHERE id = ?", Integer.class, jobId);
    if (count == null || count == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
    jdbc.update("INSERT INTO applications (job_id, applicant_name, email) VALUES (?, ?, ?)", jobId, name, email);
  }

  public List<JobApplication> findApplications() {
    return jdbc.query("SELECT id,job_id,applicant_name,email FROM applications ORDER BY id DESC",
      (rs,n) -> new JobApplication(rs.getLong("id"),rs.getLong("job_id"),rs.getString("applicant_name"),rs.getString("email")));
  }
}
