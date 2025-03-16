import React, {  useState } from "react";
import { useFetchDBJobsByID, useFetchJobs } from "../queries/queries";
import { JobSearchParams } from "../@types/JobSearchParams";
import { JobData } from "../@types/JobData";
import { Select } from "antd";

function JobOptionsPage() {

  const { Option } = Select;

  const [searchParams, setSearchParams] = useState<JobSearchParams>({
    query: "1",
    page:1,
    num_pages:1,
    country:"dk",
    language:"en",
    date_posted:"all",
    employment_types:"FULLTIME",
    radius:500

});

const [searchId, setSearchId] = useState<string | undefined>();

const {data,isLoading,isError} = useFetchDBJobsByID(searchId);
 

 
 //  const { data, isLoading, isError } = useFetchJobs(searchParams);

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
        <p>Ingen resultater blev fundet.</p>
      )}
    </div>

  );
}

export default JobOptionsPage;
