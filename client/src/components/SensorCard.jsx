export default function SensorCard({ title, value, unit }) {
  return (
    <div className="bg-white shadow-md p-4 rounded-xl text-center">
      <p className="text-gray-500 text-sm">{title}</p>
      <h2 className="text-3xl font-bold mt-2">{value} <span className="text-lg">{unit}</span></h2>
    </div>
  );
}
