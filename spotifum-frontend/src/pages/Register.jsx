import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { auth } from '../services/api'

export default function Register() {
    const [form, setForm] = useState({ email: '', password: '', name: '' })
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handle = async (e) => {
        e.preventDefault()
        const res = await auth.register(form)
        if (res.token) {
            localStorage.setItem('token', res.token)
            localStorage.setItem('user', JSON.stringify(res))
            navigate('/catalog')
        } else {
            setError(res.message || 'Registration failed')
        }
    }

    return (
        <div className="min-h-screen bg-gray-950 flex items-center justify-center">
            <div className="bg-gray-900 p-8 rounded-2xl w-full max-w-md shadow-xl">
                <h1 className="text-3xl font-bold text-green-400 mb-2">SpotifUM</h1>
                <p className="text-gray-400 mb-8">Create your account</p>
                {error && <p className="text-red-400 mb-4 text-sm">{error}</p>}
                <form onSubmit={handle} className="space-y-4">
                    <input
                        type="text"
                        placeholder="Name"
                        className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-green-400"
                        value={form.name}
                        onChange={e => setForm({ ...form, name: e.target.value })}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-green-400"
                        value={form.email}
                        onChange={e => setForm({ ...form, email: e.target.value })}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-green-400"
                        value={form.password}
                        onChange={e => setForm({ ...form, password: e.target.value })}
                    />
                    <button className="w-full bg-green-400 text-gray-950 font-bold py-3 rounded-lg hover:bg-green-300 transition">
                        Register
                    </button>
                </form>
                <p className="text-gray-400 text-sm mt-6 text-center">
                    Already have an account?{' '}
                    <Link to="/" className="text-green-400 hover:underline">Sign in</Link>
                </p>
            </div>
        </div>
    )
}