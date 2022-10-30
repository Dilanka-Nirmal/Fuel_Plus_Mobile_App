const express = require("express");
const bcrypt = require("bcryptjs");
const User = require("../models/user");
const router = express.Router();

//Sign up implementation
router.post("/add", async (req, res) => {
  try {
    const user = new User(req.body);
    await user.save();
    res.status(200).send({ user });
  } catch (error) {
    res.status(400).json({ error });
  }
});

//Sign in validation
router.post("/signIn", async (req, res) => {
  try {
    const { vehicleNo, password } = req.body;
    const user = await User.findByCredentials(vehicleNo, password);
    if (!user) {
      return res.status(400).send({ error: "Sign In Failed..!" });
    }
    res.status(200).send({ user });
  } catch (error) {
    res.status(400).send({ error });
  }
});

//Forgot password update
router.post("/forgotPassword", async (req, res) => {
  try {
    const { vehicleNo } = req.body;
    const user = await User.findByVehicleNo(vehicleNo);
    user.password = await bcrypt.hash(req.body.password, 8);
    await user.updateOne({ $set: user });
    res
      .status(200)
      .json("User account password has been updated, Successfully");
  } catch (error) {
    res.status(500).json(error);
  }
});

//Get user details
router.get("/:id", async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    res.status(200).json(user);
  } catch (error) {
    res.status(500).json(error);
  }
});

//Update user profile
router.put("/edit/:id", async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    // req.body.password = await bcrypt.hash(req.body.password, 8);
    await user.updateOne({ $set: req.body });
    res.status(200).json("User account has been updated, Successfully");
  } catch (error) {
    res.status(500).json(error);
  }
});

//Delete user profile
router.delete("/delete/:id", async (req, res) => {
  try {
    const panel = await User.findById(req.params.id);
    await panel.deleteOne();
    res.status(200).json("User account has been deleted, Successfully!");
  } catch (error) {
    res.status(500).json(error);
  }
});

//Fuel data added to user's fuel history
router.put("/join/:id", async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    await user.updateOne({
      $push: {
        history: [
          {
            fuelStation: req.body.fuelStation,
            inTime: req.body.inTime,
            outTime: req.body.outTime,
            exitTime: req.body.exitTime,
          },
        ],
      },
    });
    res.status(200).json("Fuel data added to user's fuel history.");
  } catch (err) {
    res.status(500).json(err);
  }
});

//Retrieve user's fuel history
router.get("/get/history/:id", async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    res.status(200).json(user.history);
  } catch (err) {
    res.status(500).json(err);
  }
});

module.exports = router;
