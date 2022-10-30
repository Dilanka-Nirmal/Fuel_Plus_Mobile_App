const express = require("express");
const FuelStation = require("../models/fuelStation");
const router = express.Router();

//Add fuel station
router.post("/add", async (req, res) => {
  const fuelStation = new FuelStation(req.body);

  try {
    const newFuelStation = await fuelStation.save();
    res.status(200).json(newFuelStation);
  } catch (error) {
    res.status(500).json(error);
  }
});

//New vehicle join/exit from the fuel station queue
router.put("/edit/:id", async (req, res) => {
  try {
    const fuelStation = await FuelStation.findById(req.params.id);
    await fuelStation.updateOne({ $set: req.body });
    res.status(200).send({ fuelStation });
  } catch (error) {
    res.status(400).send({ error });
  }
});

//Get all fuel station details
router.get("/home", async (req, res) => {
  FuelStation.find()
    .then((fuelStations) => {
      res.json(fuelStations);
    })
    .catch((err) => {
      console.log(err);
    });
});

//Get fuel station details
router.get("/:id", async (req, res) => {
  try {
    const fuelStation = await FuelStation.findById(req.params.id);
    res.status(200).json(fuelStation);
  } catch (error) {
    res.status(500).json(error);
  }
});

module.exports = router;
