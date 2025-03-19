import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import './styles/App.css';
import { ReactElement, useState } from 'react';
//import JobSearchForm from './pages/JobSearchForm';
import JobOptionsPage from './pages/JobOptionsPage';
import Header from './components/Header';
import HeaderText from './components/HeaderText';
import { JobSearchParams } from './@types/JobSearchParams';


const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime:600000,
      retry:0,
    },
  },
  
});
function App(): ReactElement {

  const [searchParams, setSearchParams] = useState<JobSearchParams | undefined>();

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <div>
          <Header />
          {!searchParams && <HeaderText />}
          <JobOptionsPage searchParams={searchParams} setSearchParams={setSearchParams} />
        </div>

      </QueryClientProvider>
    </>
  )
}

export default App
