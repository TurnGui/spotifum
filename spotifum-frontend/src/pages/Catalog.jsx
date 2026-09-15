import { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import { songs, albums } from '../services/api'

export default function Catalog() {
    const [songList, setSongList] = useState([])
    const [albumList, setAlbumList] = useState([])
    const [tab, setTab] = useState('songs')
    const [playing, setPlaying] = useState(null)

    useEffect(() => {
        songs.getAll().then(setSongList)
        albums.getAll().then(setAlbumList)
    }, [])

    const play = async (id) => {
        const updated = await songs.play(id)
        setPlaying(id)
        setSongList(prev => prev.map(s => s.id === id ? { ...s, playCount: updated.playCount } : s))
        setTimeout(() => setPlaying(null), 2000)
    }

    return (
        <div className="min-h-screen bg-gray-950 text-white">
            <Navbar />
            <div className="max-w-5xl mx-auto px-6 py-8">
                <h1 className="text-2xl font-bold mb-6">Music Catalog</h1>
                <div className="flex gap-4 mb-6">
                    <button onClick={() => setTab('songs')}
                            className={`px-4 py-2 rounded-lg font-medium transition ${tab === 'songs' ? 'bg-green-400 text-gray-950' : 'bg-gray-800 text-gray-300 hover:bg-gray-700'}`}>
                        Songs
                    </button>
                    <button onClick={() => setTab('albums')}
                            className={`px-4 py-2 rounded-lg font-medium transition ${tab === 'albums' ? 'bg-green-400 text-gray-950' : 'bg-gray-800 text-gray-300 hover:bg-gray-700'}`}>
                        Albums
                    </button>
                </div>

                {tab === 'songs' && (
                    <div className="space-y-2">
                        {songList.map(song => (
                            <div key={song.id} className="bg-gray-900 rounded-xl px-6 py-4 flex items-center justify-between hover:bg-gray-800 transition">
                                <div>
                                    <p className="font-medium">{song.title}</p>
                                    <p className="text-gray-400 text-sm">{song.artist} · {song.genre}</p>
                                </div>
                                <div className="flex items-center gap-6">
                                    <span className="text-gray-500 text-sm">{Math.floor(song.durationSeconds / 60)}:{String(song.durationSeconds % 60).padStart(2, '0')}</span>
                                    <span className="text-gray-500 text-sm">▶ {song.playCount}</span>
                                    <button onClick={() => play(song.id)}
                                            className={`px-4 py-1.5 rounded-lg text-sm font-medium transition ${playing === song.id ? 'bg-green-400 text-gray-950' : 'bg-gray-700 hover:bg-green-400 hover:text-gray-950'}`}>
                                        {playing === song.id ? '♪ Playing' : 'Play'}
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                {tab === 'albums' && (
                    <div className="space-y-4">
                        {albumList.map(album => (
                            <div key={album.id} className="bg-gray-900 rounded-xl p-6">
                                <p className="font-bold text-lg">{album.title}</p>
                                <p className="text-gray-400 text-sm mb-4">{album.artist} · {album.year}</p>
                                <div className="space-y-2">
                                    {album.songs.map(song => (
                                        <div key={song.id} className="flex items-center justify-between text-sm text-gray-300">
                                            <span>{song.title}</span>
                                            <span className="text-gray-500">{Math.floor(song.durationSeconds / 60)}:{String(song.durationSeconds % 60).padStart(2, '0')}</span>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    )
}