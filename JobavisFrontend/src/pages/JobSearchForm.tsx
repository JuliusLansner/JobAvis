import React, { useState } from "react";
import { useFetchJobs } from "../queries/queries";
import { JobSearchParams } from "../@types/JobSearchParams";
import { JobData } from "../@types/JobData";
function JobSearchForm() {

  const [searchParams, setSearchParams] = useState<JobSearchParams>({
    query: "Backend udvikler",
    page:1,
    num_pages:1,
    country:"dk",
    language:"da",
    date_posted:"week",
    employment_types:"FULLTIME",
    radius:100

});


  const { data, isLoading, isError } = useFetchJobs(searchParams);
console.log("DATA++",data)
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
 
  };
console.log("PARAMs++",searchParams)
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          value={searchParams.query}
          onChange={(e) =>
            setSearchParams({ ...searchParams, query: e.target.value })
          }
          placeholder="Search..."
        />
      
        <button type="submit">Search</button>
      </form>
      {isLoading && <div>Loading...</div>}
      {isError && <div>Error fetching jobs.</div>}
      {data && data.data && data.data.length > 0 ? (
        <ul>
          {data.data.map((job: JobData) => (
            <li key={job.job_id}>
              <h3>{job.job_title}</h3>
              <p>{job.employer_name}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>No results found.</p>
      )}
    </div>
  );
}

export default JobSearchForm;
