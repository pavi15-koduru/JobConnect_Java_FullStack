const jobList=document.getElementById('jobList');
const jobCount=document.getElementById('jobCount');
const dialog=document.getElementById('applyDialog');
const applyStatus=document.getElementById('applyStatus');

function escapeHtml(value){return String(value).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));}
async function loadJobs(search=''){
  jobList.innerHTML='<p class="empty">Loading jobs...</p>';
  try{
    const url=search.trim()?`/api/jobs?search=${encodeURIComponent(search.trim())}`:'/api/jobs';
    const response=await fetch(url);if(!response.ok)throw new Error();
    const jobs=await response.json();jobCount.textContent=`${jobs.length} ${jobs.length===1?'opportunity':'opportunities'}`;
    if(!jobs.length){jobList.innerHTML='<p class="empty">No matching jobs found. Try another keyword.</p>';return;}
    jobList.innerHTML=jobs.map(job=>`<article class="job-card">
      <div class="company-mark">${escapeHtml(job.company.charAt(0).toUpperCase())}</div>
      <h3>${escapeHtml(job.title)}</h3><p class="company">${escapeHtml(job.company)}</p>
      <p class="meta">Location: ${escapeHtml(job.location)}</p><p class="meta">${escapeHtml(job.description)}</p>
      <div class="tag-row">${job.skills.split(',').map(s=>`<span class="tag">${escapeHtml(s.trim())}</span>`).join('')}</div>
      <div class="card-bottom"><small>Entry level</small><button class="apply-button" data-id="${job.id}" data-title="${escapeHtml(job.title)}">Apply now →</button></div></article>`).join('');
    document.querySelectorAll('.apply-button').forEach(b=>b.addEventListener('click',()=>openApplication(b.dataset.id,b.dataset.title)));
  }catch(e){jobList.innerHTML='<p class="empty">Cannot connect to backend. Check that Spring Boot is running and MySQL is configured.</p>';jobCount.textContent='Unavailable';}
}
function openApplication(id,title){document.getElementById('jobId').value=id;document.getElementById('selectedJob').textContent=title;applyStatus.textContent='';dialog.showModal();}
document.getElementById('closeDialog').addEventListener('click',()=>dialog.close());
document.getElementById('searchForm').addEventListener('submit',e=>{e.preventDefault();loadJobs(document.getElementById('searchInput').value);});
document.getElementById('applyForm').addEventListener('submit',async e=>{
  e.preventDefault();applyStatus.textContent='Submitting...';
  const payload={jobId:Number(document.getElementById('jobId').value),applicantName:document.getElementById('applicantName').value.trim(),email:document.getElementById('email').value.trim()};
  try{
    const response=await fetch('/api/applications',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(payload)});
    const data=await response.json();if(!response.ok)throw new Error(data.message||data.error||'Application failed');
    applyStatus.textContent='Application submitted successfully!';document.getElementById('applyForm').reset();setTimeout(()=>dialog.close(),1100);
  }catch(err){applyStatus.textContent=err.message||'Unable to submit application.';}
});
loadJobs();
