import axios from "axios";

export const searchAxios = axios.create({
    baseURL: "http://localhost:8080/api",
    withCredentials:true,
});