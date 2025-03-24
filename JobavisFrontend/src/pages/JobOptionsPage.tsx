import React, { useEffect, useState } from "react";
import {
  useFetchDetailsById,
  useFetchJobs,
  usePrefetchJobDetails,
} from "../queries/queries";
import { JobSearchParams } from "../@types/JobSearchParams";
import { JobData } from "../@types/JobData";
import { Select } from "antd";
import { JobDetails } from "../@types/JobDetails";
import { City } from "country-state-city";
import { ICity } from "../@types/cityInterface";
interface JobOptionsPageProps {
  searchParams: JobSearchParams | undefined;
  setSearchParams: React.Dispatch<
    React.SetStateAction<JobSearchParams | undefined>
  >;
}

function JobOptionsPage({
  searchParams,
  setSearchParams,
}: JobOptionsPageProps) {
  const { Option } = Select;

  //const [searchParams, setSearchParams] = useState<JobSearchParams>(); // NOT BEING USED ANYMORE
  const [searchQuery, setSearchQuery] = useState<JobSearchParams |undefined>();
  const [cities, setCities] = useState<ICity[] | undefined>([]);
  //const [searchId, setSearchId] = useState<string>();

  //Fetching from DB:
  //const {data:db,isLoading:isldb,isError:isle} = useFetchDBJobsByID(searchId);
  //const {data:dbx,isLoading:isloadingSelect,isError:isErrorselect} = useFetchDBDetailsByID(selectedJobId);

  // Prefetching job details on hover
  const prefetchJobDetails = usePrefetchJobDetails();

  // State for selected job ID
  const [selectedJobId, setSelectedJobId] = useState<string>();
  // Prevent API calls when clicking the same job and set the selected job ID
  const handleJobClick = (jobId: string) => {
    if (selectedJobId !== jobId) {
      setSelectedJobId(jobId);
    }
  };

  console.log("SID++:" + selectedJobId);
  useEffect(() => {
    const denmarkCities = City.getCitiesOfCountry("DK") as ICity[] |undefined;
    setCities(denmarkCities);
  }, []);
  //Fetching from RapidAPI:
  const { data: details } = useFetchDetailsById(selectedJobId);

  const { data: dbd, isLoading, isError } = useFetchJobs(searchParams);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSearchParams(searchQuery);
    setSearchQuery("");
  };

  console.log("PARAMs++", searchParams);
  return (
    <div>
      <div className="form-container">
        <form onSubmit={handleSubmit}>
          <input
            value={searchQuery?.query || ""}
            onChange={(e) =>
              setSearchQuery({ ...searchQuery, query: e.target.value })
            }
            placeholder="Søg..."
            className="query-input"
          />

          <Select
          showSearch
            value={searchQuery?.job_location || "Lokation"}
           onChange={(value) => setSearchQuery({ ...searchQuery, job_location: value })}
            placeholder="Lokation"
            
            style={{width:200}}
          >
            {cities?.map((city,index)=> (
              <Option key={city.id+`${index}`} value={city.name}>
                {city.name}
              </Option>
            ))}
          </Select>

          <Select
            value={searchQuery?.employment_types || "Type"}
            onChange={(value) =>
              setSearchQuery({ ...searchQuery, employment_types: value })
            }
            placeholder="Jobtype"
          >
            <Option value="FULLTIME">Fuldtid</Option>
            <Option value="PARTTIME">Deltid</Option>
            <Option value="CONTRACTOR">Kontrakt</Option>
            <Option value="INTERN">Praktikant</Option>
          </Select>

          <button className="search-btn" type="submit">
            Søg
          </button>
        </form>
      </div>

      {isLoading && <div>Loading...</div>}
      {isError && <div>En fejl opstod ved søgning efter jobs.</div>}

      <div className="job-results">
        {dbd && dbd.data && dbd.data.length > 0 ? (
          <ul className="jobs-list">
            {dbd.data.map((job: JobData) => (
              <li
                className={`single-job ${
                  selectedJobId === job.job_id ? "active" : ""
                }`}
                key={job.job_id}
                onClick={() => handleJobClick(job.job_id)}
                onMouseEnter={() => prefetchJobDetails(job.job_id)} // Prefetch on hover
                /*onClick={() => {
                  console.log("Clicked job ID:", job.job_id);
                  setSelectedJobId(job.job_id);
                }}*/ // Set job ID on click
              >
                <div className="jobcontainer-left">
                  <img
                    src={job.employer_logo || ""}
                    className="employer-logo"
                  />
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
          /*<p>Søg efter jobs ved brug af filtrene ovenover.</p>*/
          ""
        )}

        {dbd &&
          dbd.data &&
          dbd.data.length > 0 &&
          selectedJobId &&
          details &&
          details.data &&
          details.data.length > 0 && (
            <ul className="job-details">
              {details.data
                .filter((job: JobDetails) => job.job_id === selectedJobId) // Match Job ID
                .map((job: JobDetails) => (
                  <li className="single-job-details" key={job.job_id}>
                    <div className="details-top">
                      <div>
                        <img
                          src={job.employer_logo}
                          className="employer-logo"
                        />
                      </div>
                      <div>
                        <h3 className="job-details-title">{job.job_title}</h3>
                        <p className="employer-name">{job.employer_name}</p>
                        <a
                          href={job.job_apply_link}
                          className="apply-btn"
                          target="_blank"
                          rel="noopener noreferrer"
                        >
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
                                ? new Date(
                                    job.job_posted_at_datetime_utc
                                  ).toLocaleDateString("da-DK", {
                                    year: "numeric",
                                    month: "long",
                                    day: "numeric",
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
