import React, {  useState } from "react";
import { useFetchDBJobsByID, useFetchDetailsById, useFetchJobs,useFetchDBDetailsByID } from "../queries/queries";
import { JobSearchParams } from "../@types/JobSearchParams";
import { JobData } from "../@types/JobData";
import { Select } from "antd";
import { JobDetails } from "../@types/JobDetails";

function JobOptionsPage() {

  const { Option } = Select;

  const [searchParams, setSearchParams] = useState<JobSearchParams>({
    query: "asdasdasd",
    page:1,
    num_pages:1,
    country:"dk",
    language:"en",
    date_posted:"all",
    employment_types:"FULLTIME",
    radius:500

});

const [searchId, setSearchId] = useState<string | undefined>();

// State for selected job ID
const [selectedJobId, setSelectedJobId] = useState<string | undefined>();

//Fetching from DB:
const {data:db,isLoading,isError} = useFetchDBJobsByID(searchId);
const {data:dbd} = useFetchDBDetailsByID(selectedJobId);



console.log("SID++:"+selectedJobId)



//Fetching from RapidAPI:
//const {data:details} = useFetchDetailsById();
//const { data, isLoading, isError } = useFetchJobs();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSearchId(searchParams.query) 
  }; 


console.log("PARAMs++",searchParams)
  return (
    <div>

    <div className="form-container">
      <form onSubmit={handleSubmit}>
        <input
          value={searchParams.query}
          onChange={(e) =>
            setSearchParams({ ...searchParams, query: e.target.value })
          }
          placeholder="Søg..."
          className="query-input"
        />

        <input
          value=""
          placeholder="Keywords..."
          className="keyword-input"
        />
       
       
        <Select
          value={searchParams.country}
          onChange={(value) => setSearchParams({ ...searchParams, country: value })}
          placeholder="Lokation"
        >
          <Option value="dk">Danmark</Option>
        </Select>

        <Select
          value={searchParams.employment_types}
          onChange={(value) => setSearchParams({ ...searchParams, employment_types: value })}
          placeholder="Jobtype"
        >
          <Option value="FULLTIME">Fuldtid</Option>
          <Option value="">Deltid</Option>
          <Option value="">Kontrakt</Option>
          <Option value="">Praktikant</Option>
          <Option value="">Andet</Option>
        </Select>


        <Select
          value={searchParams.date_posted}
          onChange={(value) => setSearchParams({ ...searchParams, date_posted: value})}
          placeholder="Opslået"
        >
          <Option value="all">Alle tidspunkter</Option>
          <Option value="">Seneste måned</Option>
          <Option value="">Seneste uge</Option>
          <Option value="">Seneste 24 timer</Option>
        </Select>

        <input
          value={searchParams.radius}
          onChange={(e) =>
            setSearchParams({ ...searchParams, radius: Number(e.target.value) })
          }
          placeholder="Radius"
          className="radius-input"
        />

        <button className="search-btn" type="submit">Søg</button>
      </form>
    </div>

      {isLoading && <div>Loading...</div>}
      {isError && <div>En fejl opstod ved søgning efter jobs.</div>}


      <div className="job-results">
      {db && db.data && db.data.length > 0 ? (
        <ul className="jobs-list">
          {db.data.map((job: JobData) => (
            <li className={`single-job ${selectedJobId === job.job_id ? "active" : ""}`} 
                key={job.job_id}
                onClick={() => {
                  console.log("Clicked job ID:", job.job_id);
                  setSelectedJobId(job.job_id);
                }} // Set job ID on click
                >
              <div className="jobcontainer-left">
              <img src={job.employer_logo} className="employer-logo"/>
              </div>
              <div className="jobcontainer-middle">
                <h3 className="job-title">{job.job_title}</h3>
                <p className="employer-name">{job.employer_name}</p>
                <p className="job-location">{job.job_location}</p>
              </div>
              <div className="jobcontainer-right">
                <p className="employment-type">{job.job_employment_type}</p>
              </div>
             
            </li>
          ))}  
          
        </ul>

        
        ) : (
        <p>Ingen resultater blev fundet.</p>
      )}


      {selectedJobId && dbd && dbd.data && dbd.data.length > 0 && (
        <ul className="job-details">
          {dbd.data
            .filter((job: JobDetails) => job.job_id === selectedJobId) // Match Job ID
            .map((job: JobDetails) => (
              <li className="single-job-details" key={job.job_id}>
                <div className="details-top">
                  <div>
                    <img src={job.employer_logo} className="employer-logo" />
                  </div>
                  <div>
                    <h3 className="job-details-title">{job.job_title}</h3>
                    <p className="employer-name">{job.employer_name}</p>
                    <a href={job.job_apply_link} className="apply-btn" target="_blank" rel="noopener noreferrer">
                      Søg job
                    </a>
                  </div>
                </div>
                <div className="details-middle">
                  <table>
                    <thead>
                      <tr>
                        <th scope="col">Opslået</th>
                        <th scope="col">Jobtype</th>
                        <th scope="col">Lokation</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          {job.job_posted_at_datetime_utc
                            ? new Date(job.job_posted_at_datetime_utc).toLocaleDateString("da-DK", {
                                year: "numeric",
                                month: "long",
                                day: "numeric",
                                hour: "2-digit",
                                minute: "2-digit",
                              })
                            : "Ukendt dato"}
                        </td>
                        <td>{job.job_employment_type}</td>
                        <td>{job.job_location}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div className="details-bottom">
                  <p className="about-job">Om jobbet</p>
                  <p className="job-description">
                    {job.job_description.split("\n").map((line, index) => (
                      <React.Fragment key={index}>
                        {line}
                        <br />
                      </React.Fragment>
                    ))}
                  </p>
                </div>
              </li>
            ))}
        </ul>
      )}
      </div>
    </div>

  );
}

export default JobOptionsPage;
