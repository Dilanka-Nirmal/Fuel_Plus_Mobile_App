const mongoose = require("mongoose");

const fuelStationSchema = mongoose.Schema({
  stationName: {
    type: String,
    required: true,
    trim: true,
  },
  stationCode: {
    type: String,
    required: true,
    unique: true,
  },
  fuelStatus: {
    type: String,
    required: true,
  },
  petrolQueue: {
      type: Number,
      default: 0
  },
  dieselQueue: {
      type: Number,
      default: 0,
  },
  fuelArrivalTime: {
    type: Date,
    default: null,
  },
  fuelFinishTime: {
    type: Date,
    default: null,
  },
});

const FuelStation = mongoose.model("FuelStation", fuelStationSchema);

module.exports = FuelStation;
