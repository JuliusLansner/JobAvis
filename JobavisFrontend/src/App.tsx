import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import './styles/App.css';
import { ReactElement } from 'react';
//import JobSearchForm from './pages/JobSearchForm';
import JobOptionsPage from './pages/JobOptionsPage';
import Header from './components/Header';


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
          <Header />
          <JobOptionsPage />
        </div>

      </QueryClientProvider>
    </>
  )
}

export default App
