export interface JobDetails{
    job_id: string;
    job_title: string;
    employer_name: string;
    employer_logo?: string;
    employer_website?: string;
    job_publisher?: string;
    job_employment_type?: string;
    job_apply_link?: string;
    apply_options?: string[];
    job_description: string;
    job_is_remote?: boolean;
    job_posted_at?: string;
    job_posted_at_timestamp?: string;
    job_posted_at_datetime_utc?: string;
    job_location?: string;
    job_city?: string;
    job_country?: string;
    job_benefits?: string;
    employer_reviews?: string[];
}