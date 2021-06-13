const path = require('path');

module.exports = {
	extends: [
		'eslint:recommended',
		'plugin:import/errors',
		'plugin:react/recommended',
		'plugin:jsx-a11y/recommended',
		'prettier',
	],
	rules: {
		'react/proptypes': 0,
		'no-console': 1,
		'react-hooks/rules-of-hooks': 2,
		'react-hooks/exhaustive-deps': 1,
	},
	plugins: ['react', 'import', 'jsx-a11y', 'react-hooks', '@babel'],
	parser: '@babel/eslint-parser',
	parserOptions: {
		ecmaVersion: 2018,
		sourceType: 'module',
		ecmaFeatures: {
			jsx: true,
		},
		requireConfigFile: 'false',
		babelOptions: { configFile: path.join(__dirname, 'babel.config.json') },
	},
	env: { es6: true, browser: true, node: true },
	settings: {
		react: {
			version: 'detect',
		},
	},
};
