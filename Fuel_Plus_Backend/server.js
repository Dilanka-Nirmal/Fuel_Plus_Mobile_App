const express = require("express");
const port = process.env.PORT;
require("./config/database");

//Import routes
const userRouter = require("./routers/user");
const fuelStation = require("./routers/fuelStation");


const app = express();

//Init middleware
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

//Routes
app.use("/api/user", userRouter);
app.use("/api/fuelStation", fuelStation);


app.listen(port, () => {
  console.log(`Server is running on PORT ${port}`)
})
