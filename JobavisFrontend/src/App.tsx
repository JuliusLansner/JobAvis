import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import './App.css'
import { ReactElement } from 'react';
import JobSearchForm from './pages/JobSearchForm';


const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime:600000,
      retry:0,
    },
  },
  
});
function App(): ReactElement {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <div>
          <JobSearchForm/>
        </div>

      </QueryClientProvider>
    </>
  )
}

export default App
