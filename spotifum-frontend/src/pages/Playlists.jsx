import { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import { playlists, songs } from '../services/api'

export default function Playlists() {
    const [playlistList, setPlaylistList] = useState([])
    const [songList, setSongList] = useState([])
    const [showForm, setShowForm] = useState(false)
    const [form, setForm] = useState({ name: '', kind: 'CUSTOM' })

    useEffect(() => {
        playlists.getAll().then(setPlaylistList)
        songs.getAll().then(setSongList)
    }, [])

    const create = async (e) => {
        e.preventDefault()
        const res = await playlists.create(form)
        setPlaylistList(prev => [...prev, res])
        setShowForm(false)
        setForm({ name: '', kind: 'CUSTOM' })
    }

    const remove = async (id) => {
        await playlists.delete(id)
        setPlaylistList(prev => prev.filter(p => p.id !== id))
    }

    return (
        <div className="min-h-screen bg-gray-950 text-white">
            <Navbar />
            <div className="max-w-5xl mx-auto px-6 py-8">
                <div className="flex items-center justify-between mb-6">
                    <h1 className="text-2xl font-bold">My Playlists</h1>
                    <button onClick={() => setShowForm(!showForm)}
                            className="bg-green-400 text-gray-950 font-bold px-4 py-2 rounded-lg hover:bg-green-300 transition">
                        + New Playlist
                    </button>
                </div>

                {showForm && (
                    <form onSubmit={create} className="bg-gray-900 rounded-xl p-6 mb-6 space-y-4">
                        <input
                            placeholder="Playlist name"
                            className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-green-400"
                            value={form.name}
                            onChange={e => setForm({ ...form, name: e.target.value })}
                        />
                        <select
                            className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-green-400"
                            value={form.kind}
                            onChange={e => setForm({ ...form, kind: e.target.value })}>
                            <option value="CUSTOM">Custom</option>
                            <option value="RANDOM">Random Mix</option>
                        </select>
                        <button className="bg-green-400 text-gray-950 font-bold px-6 py-2 rounded-lg hover:bg-green-300 transition">
                            Create
                        </button>
                    </form>
                )}

                <div className="space-y-4">
                    {playlistList.map(p => (
                        <div key={p.id} className="bg-gray-900 rounded-xl p-6">
                            <div className="flex items-center justify-between mb-3">
                                <div>
                                    <p className="font-bold text-lg">{p.name}</p>
                                    <p className="text-gray-400 text-sm">{p.kind} · {p.songs.length} songs</p>
                                </div>
                                <button onClick={() => remove(p.id)} className="text-red-400 hover:text-red-300 text-sm transition">Delete</button>
                            </div>
                            {p.songs.length > 0 && (
                                <div className="space-y-1">
                                    {p.songs.map(s => (
                                        <div key={s.id} className="text-sm text-gray-400 flex justify-between">
                                            <span>{s.title}</span>
                                            <span>{s.artist}</span>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}