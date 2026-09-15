import { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import { leaderboard } from '../services/api'

export default function Leaderboard() {
    const [data, setData] = useState(null)

    useEffect(() => {
        leaderboard.get().then(setData)
    }, [])

    const cards = data ? [
        { label: ' Most Played Song', value: data.mostPlayedSong },
        { label: ' Most Played Artist', value: data.mostPlayedArtist },
        { label: ' Most Played Genre', value: data.mostPlayedGenre },
        { label: ' Most Active Listener', value: data.mostActiveListener },
    ] : []

    return (
        <div className="min-h-screen bg-gray-950 text-white">
            <Navbar />
            <div className="max-w-3xl mx-auto px-6 py-8">
                <h1 className="text-2xl font-bold mb-6">Leaderboard</h1>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    {cards.map(card => (
                        <div key={card.label} className="bg-gray-900 rounded-xl p-6">
                            <p className="text-gray-400 text-sm mb-2">{card.label}</p>
                            <p className="text-white font-bold text-lg">{card.value}</p>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}