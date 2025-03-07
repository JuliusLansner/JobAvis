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