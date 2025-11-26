import { useEffect, useState } from "react";

const BACKEND_URL = "https://perfect-wholeness-production-2240.up.railway.app";

// Simple Sensor Card Component
function SensorCard({ title, value, unit }) {
  return (
    <div className="bg-white rounded-lg shadow p-4">
      <h3 className="text-sm text-gray-600 mb-1">{title}</h3>
      <div className="flex items-baseline">
        <span className="text-2xl font-bold text-gray-900">{value}</span>
        <span className="ml-2 text-gray-500">{unit}</span>
      </div>
    </div>
  );
}

// Simple Chart Component
function SensorChart({ data, dataKey, label }) {
  if (!data || data.length === 0) {
    return (
      <div className="bg-white rounded-lg shadow p-4">
        <h3 className="text-lg font-semibold mb-4">{label}</h3>
        <p className="text-gray-500">No data available</p>
      </div>
    );
  }

  const values = data.map(d => d[dataKey]).filter(v => v != null);
  const max = Math.max(...values);
  const min = Math.min(...values);
  
  return (
    <div className="bg-white rounded-lg shadow p-4">
      <h3 className="text-lg font-semibold mb-4">{label}</h3>
      <div className="h-48 flex items-end space-x-1">
        {data.slice(-20).map((item, idx) => {
          const value = item[dataKey];
          const height = value ? ((value - min) / (max - min || 1)) * 100 : 0;
          return (
            <div
              key={idx}
              className="flex-1 bg-blue-500 rounded-t"
              style={{ height: `${height}%`, minHeight: '2px' }}
              title={`${value} at ${new Date(item.receivedAt).toLocaleTimeString()}`}
            />
          );
        })}
      </div>
      <div className="flex justify-between mt-2 text-xs text-gray-500">
        <span>Min: {min.toFixed(1)}</span>
        <span>Max: {max.toFixed(1)}</span>
      </div>
    </div>
  );
}

export default function Dashboard() {
  const [latest, setLatest] = useState(null);
  const [history, setHistory] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      // Fetch latest reading using fetch API
      try {
        console.log("🔄 Fetching latest reading...");
        const latestRes = await fetch(`${BACKEND_URL}/api/readings/latest`, {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          },
        });
        
        if (latestRes.ok) {
          const data = await latestRes.json();
          console.log("✅ Latest data:", data);
          setLatest(data || null);
        } else if (latestRes.status === 204) {
          console.log("⚠️ No content - database is empty");
          setLatest(null);
        } else {
          const errorData = await latestRes.json().catch(() => ({}));
          console.error("❌ Error response:", latestRes.status, errorData);
          setError(`Latest: ${latestRes.status} - ${errorData.error || 'Unknown error'}`);
        }
      } catch (err) {
        console.error("❌ Error fetching latest:", err);
        setError(`Latest: ${err.message}`);
      }

      // Fetch history using fetch API
      try {
        console.log("🔄 Fetching history...");
        const historyRes = await fetch(`${BACKEND_URL}/api/sensordata`, {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          },
        });
        
        if (historyRes.ok) {
          const data = await historyRes.json();
          console.log("✅ History data:", data);
          setHistory(Array.isArray(data) ? data : []);
        } else {
          console.error("❌ History error:", historyRes.status);
          setHistory([]);
        }
      } catch (err) {
        console.error("❌ Error fetching history:", err);
        setHistory([]);
      }

      setLoading(false);
    };

    fetchData();
    const interval = setInterval(fetchData, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-6">Patient Monitoring Dashboard</h1>

      {/* Debug Info Panel */}
      <div className="mb-4 p-4 bg-white rounded-lg shadow">
        <h2 className="font-semibold mb-2">🔍 Debug Info:</h2>
        <p className="text-sm">Backend: {BACKEND_URL}</p>
        <p className="text-sm">Status: {loading ? "⏳ Loading..." : "✅ Ready"}</p>
        {error && <p className="text-sm text-red-600">❌ Error: {error}</p>}
        <p className="text-sm">Latest data: {latest ? "✅ Available" : "❌ No data"}</p>
        <p className="text-sm">History records: {history.length}</p>
        {latest && (
          <details className="mt-2">
            <summary className="cursor-pointer text-sm text-blue-600 hover:text-blue-800">
              Show latest data
            </summary>
            <pre className="text-xs mt-2 bg-gray-50 p-2 rounded overflow-auto max-h-40">
              {JSON.stringify(latest, null, 2)}
            </pre>
          </details>
        )}
      </div>

      {/* Sensor Cards */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
        <SensorCard 
          title="Room Temp" 
          value={latest?.roomTemp?.toFixed(1) ?? "--"} 
          unit="°C" 
        />
        <SensorCard 
          title="Humidity" 
          value={latest?.humidity?.toFixed(1) ?? "--"} 
          unit="%" 
        />
        <SensorCard 
          title="Body Temp" 
          value={latest?.waterTempC?.toFixed(1) ?? "--"} 
          unit="°C" 
        />
        <SensorCard 
          title="Heart Rate" 
          value={latest?.avgBpm ?? latest?.bpm ?? "--"} 
          unit="BPM" 
        />
      </div>

      {/* Charts */}
      <div className="space-y-6">
        <SensorChart data={history} dataKey="roomTemp" label="Room Temperature Over Time" />
        <SensorChart data={history} dataKey="waterTempC" label="Body Temperature Over Time" />
        <SensorChart data={history} dataKey="avgBpm" label="Heart Rate Over Time" />
      </div>
    </div>
  );
}