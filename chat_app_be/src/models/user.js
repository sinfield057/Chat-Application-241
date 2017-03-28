import mongoose from 'mongoose'
const Schema = mongoose.Schema;

const userSchema = new Schema({
	username: String,
	password: String
});

module.exports = mongoose.model( 'User', userSchema );