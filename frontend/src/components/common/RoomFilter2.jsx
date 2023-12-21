import React, { useState } from "react";

const RoomFilter2 = ({ data, setFilteredData }) => {
  const [selectedFloor, setSelectedFloor] = useState("");

  const handleSelectChange = (e) => {
    const floor = e.target.value;
    setSelectedFloor(floor);

    const filteredRooms = data.filter((room) =>
      room.floor.toString() === floor.toString()
    );

    setFilteredData(filteredRooms);
  };

  const clearFilter = () => {
    setSelectedFloor("");
    setFilteredData(data);
  };

  const uniqueFloors = [...new Set(data.map((room) => room.floor))];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="floor-filter">
        Filter rooms by floor
      </span>
      <select
        className="form-select"
        aria-label="floor filter"
        value={selectedFloor}
        onChange={handleSelectChange}
      >
        <option value="">Select a floor to filter...</option>
        {uniqueFloors.map((floor, index) => (
          <option key={index} value={floor}>
            {floor}
          </option>
        ))}
      </select>
      <button className="btn btn-hotel" type="button" onClick={clearFilter}>
        Clear Filter
      </button>
    </div>
  );
};

export default RoomFilter2;
