export interface JobSearchParams{
    query?: string;
    page?: number;
    num_pages?: number;
    country?: string;
    language?: string;
    date_posted?: string;
    employment_types?: string;
    job_requirements?: string;
    radius?: string;
    keyWords?:string;
    job_location?:string;
}