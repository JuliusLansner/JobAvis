import { useQuery } from "@tanstack/react-query";
import { searchAxios } from "../config/axios";
import { JobSearchParams } from "../@types/JobSearchParams";
import { useQueryClient } from "@tanstack/react-query";


export const useFetchJobs = (searchParams:JobSearchParams | undefined) =>{
   return useQuery({
        queryKey:["fetchData",searchParams],
        queryFn: async() => {
            if (!searchParams) return Promise.reject(new Error("No search parameters provided")); 
            const {data} = await searchAxios.get("/search",{
                
             params:searchParams
            });
            return data;
        },
        enabled: !!searchParams,
        staleTime:600000
    });
}

export const useFetchDBJobsByID = (id:string |undefined) =>{
    return useQuery({
        queryKey:["fetchDb",id],
        queryFn: async()=>{
            if (!id) return Promise.reject(new Error("No ID provided")); // Prevents API call if id is undefined
            const{data} = await searchAxios.get('/dbfetch/'+id)
                
            return data;
        },
        enabled: !!id, // Ensures query runs only when id is truthy
        staleTime:600000
    })
}

export const useFetchDBDetailsByID = (id:string |undefined) =>{
    return useQuery({
        queryKey:["fetchDbD",id],
        queryFn: async()=>{
            if (!id) return null;  // Prevent API call if ID is undefined

            const{data} = await searchAxios.get('/dbfetchdetails/'+id)
                
            return data;
        },
        enabled: !!id, // Only fetch when ID is available
        staleTime:600000
    })
}

export const useFetchDetailsById = (id:string |undefined) =>{
    return useQuery({
        queryKey:["fetchDetails",id],
        queryFn:async() =>{
            if (!id) return Promise.reject(new Error("No ID provided"));
            const {data} = await searchAxios.get("/job-details",{
                params:{
                    job_id:id,
                    country:"dk"
                }
            })
            
            return data;
        },
        staleTime:600000,
        enabled:!!id

    })
}


// Prefetch job details to load instantly when clicked
export const usePrefetchJobDetails = () => {
    const queryClient = useQueryClient(); 

    const prefetchJobDetails = (jobId: string) => {
        queryClient.prefetchQuery({
            queryKey: ["fetchDbD", jobId], 
            queryFn: async () => {
                if (!jobId) return Promise.reject(new Error("No ID provided"));
                const { data } = await searchAxios.get("/job-details",{
                    params:{
                        job_id:jobId,
                        country:"dk"
                    }
                });
                return data;
            },
            staleTime:600000
        });
    };

    return prefetchJobDetails; // Return the function so it can be used elsewhere
};