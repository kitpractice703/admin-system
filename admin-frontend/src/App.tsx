import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import UserPage from "./pages/UserPage";
import NoticePage from "./pages/NoticePage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Navigate to="/users" />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/users" element={<UserPage />} />
        <Route path="/notices" element={<NoticePage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
