import { Link, useNavigate } from 'react-router-dom'

export default function Navbar() {
    const navigate = useNavigate()
    const u = JSON.parse(localStorage.getItem('user') || '{}')

    const logout = () => {
        localStorage.clear()
        navigate('/')
    }

    return (
        <nav className="bg-gray-900 border-b border-gray-800 px-6 py-4 flex items-center justify-between">
            <div className="flex items-center gap-8">
                <span className="text-green-400 font-bold text-xl">SpotifUM</span>
                <div className="flex gap-6">
                    <Link to="/catalog" className="text-gray-300 hover:text-white transition">Catalog</Link>
                    <Link to="/playlists" className="text-gray-300 hover:text-white transition">Playlists</Link>
                    <Link to="/leaderboard" className="text-gray-300 hover:text-white transition">Leaderboard</Link>
                </div>
            </div>
            <div className="flex items-center gap-4">
                <span className="text-gray-400 text-sm">{u.name} · {u.plan}</span>
                <button onClick={logout} className="text-gray-400 hover:text-red-400 text-sm transition">Logout</button>
            </div>
        </nav>
    )
}