import { useQuery } from "@tanstack/react-query";
import { searchAxios } from "../config/axios";
import { JobSearchParams } from "../@types/JobSearchParams";


export const useFetchJobs = (searchParams:JobSearchParams) =>{
   return useQuery({
        queryKey:["fetchData"],
        queryFn: async() => {
            const {data} = await searchAxios.get("/search",{
                
             params:searchParams
            });
            return data;
        },
        staleTime:600000
    });
}

export const useFetchDBJobsByID = (id:string |undefined) =>{
    return useQuery({
        queryKey:["fetchDb",id],
        queryFn: async()=>{
            const{data} = await searchAxios.get('/dbfetch/'+id)
                
            return data;
        },
        staleTime:600000
    })
}
export const useFetchDBDetailsByID = (id:string |undefined) =>{
    return useQuery({
        queryKey:["fetchDbD",id],
        queryFn: async()=>{
            const{data} = await searchAxios.get('/dbfetchdetails/'+id)
                
            return data;
        },
        staleTime:600000
    })
}

export const useFetchDetailsById = (id:string | undefined) =>{
    return useQuery({
        queryKey:["fetchDetails",id],
        queryFn:async() =>{
            const {data} = await searchAxios.get("/job-details/"+id)
            return data;
        }
    })
}