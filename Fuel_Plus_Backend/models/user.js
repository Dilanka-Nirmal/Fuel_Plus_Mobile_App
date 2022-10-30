const mongoose = require("mongoose");
const bcrypt = require("bcryptjs");

const userSchema = mongoose.Schema({
  firstName: {
    type: String,
    required: true,
    trim: true,
  },
  lastName: {
    type: String,
    required: true,
    trim: true,
  },
  phoneNo: {
    type: String,
    required: true,
    trim: true,
  },
  vehicleNo: {
    type: String,
    required: true,
    unique: true,
    uppercase: true,
    trim: true,
  },
  fuelType: {
    type: String,
    required: true,
    trim: true,
  },
  password: {
    type: String,
    required: true,
    trim: true,
    minLength: 5,
  },
  userType: {
    type: Boolean,
    default: false,
    required: true,
  },
  history: [
    {
      fuelStation: {
        type: String,
        default: null,
      },
      inTime: {
        type: Date,
        default: null,
      },
      outTime: {
        type: Date,
        default: null,
      },
      exitTime: {
        type: Date,
        default: null,
      },
    },
  ],
  tokens: [
    {
      token: {
        type: String,
        required: true,
      },
    },
  ],
});

//Encrypt the entered password
userSchema.pre("save", async function (next) {
  const user = this;
  if (user.isModified("password")) {
    user.password = await bcrypt.hash(user.password, 8);
  }
});

//Filter the object via credentials
userSchema.statics.findByCredentials = async (vehicleNo, password) => {
  const user = await User.findOne({ vehicleNo });
  if (!user) {
    throw new Error({ error: "Invalid Sign In Credentials" });
  }

  const isPasswordMatch = await bcrypt.compare(password, user.password);
  if (!isPasswordMatch) {
    throw new Error({ error: "Invalid Sign In Credentials" });
  }

  return user;
};

//Filter the object via vehicle no
userSchema.statics.findByVehicleNo = async (vehicleNo) => {
  const user = await User.findOne({ vehicleNo });
  if (!user) {
    throw new Error({ error: "Invalid Sign In Credentials" });
  }
  return user;
};

const User = mongoose.model("User", userSchema);

module.exports = User;
