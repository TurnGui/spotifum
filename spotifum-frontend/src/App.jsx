import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Catalog from './pages/Catalog'
import Playlists from './pages/Playlists'
import Leaderboard from './pages/Leaderboard'

const PrivateRoute = ({ children }) => {
  return localStorage.getItem('token') ? children : <Navigate to="/" />
}

export default function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/catalog" element={<PrivateRoute><Catalog /></PrivateRoute>} />
          <Route path="/playlists" element={<PrivateRoute><Playlists /></PrivateRoute>} />
          <Route path="/leaderboard" element={<PrivateRoute><Leaderboard /></PrivateRoute>} />
        </Routes>
      </BrowserRouter>
  )
}