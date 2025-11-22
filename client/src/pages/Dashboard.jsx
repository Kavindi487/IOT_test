import { useEffect, useState } from "react";
import axios from "axios";
import SensorCard from "../components/SensorCard";
import SensorChart from "../components/SensorChart";

export default function Dashboard() {
  const [latest, setLatest] = useState({});
  const [history, setHistory] = useState([]);

  useEffect(() => {
  const fetchData = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/readings/latest");
      setLatest(res.data || {});
    } catch (err) {
      console.error(err);
      setLatest({});
    }

    try {
      const resHistory = await axios.get("http://localhost:8080/api/readings");
      setHistory(resHistory.data || []);
    } catch (err) {
      console.error(err);
      setHistory([]);
    }
  };

  fetchData(); // call async function

  const interval = setInterval(fetchData, 5000); // refresh every 5s

  return () => clearInterval(interval);
}, []);


  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-6">Patient Monitoring Dashboard</h1>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
        <SensorCard title="Room Temp" value={latest.roomTemp} unit="°C" />
        <SensorCard title="Humidity" value={latest.humidity} unit="%" />
        <SensorCard title="Body Temp" value={latest.waterTempC} unit="°C" />
        <SensorCard title="Heart Rate" value={latest.bpm} unit="BPM" />
      </div>

      <div className="space-y-6">
        <SensorChart data={history} dataKey="roomTemp" label="Room Temperature Over Time" />
        <SensorChart data={history} dataKey="bpm" label="Heart Rate Over Time" />
      </div>
    </div>
  );
}
