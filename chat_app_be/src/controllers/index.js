import express from 'express'
const router = express.Router();

router.use( '/user', require( './userController' ) );
router.use( '/room', require( './roomController' ) );

module.exports = router;