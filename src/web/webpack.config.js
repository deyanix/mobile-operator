const path = require('path');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

const isDevelopment = process.env.NODE_ENV !== 'production';

module.exports = {
	mode: isDevelopment ? 'development' : 'production',
	entry: './scripts/index.ts',
	devtool: isDevelopment && 'eval-source-map',
	output: {
		filename: 'app.js',
		path: path.resolve(__dirname, '../main/resources/static'),
		publicPath: '/',
	},
	module: {
		rules: [
			{
				test: /\.m?[tj]sx?$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
					options: {
						presets: [
							'@babel/env',
						],
						cacheDirectory: true,
					},
				},
			},
			{
				test: /\.tsx?$/,
				loader: 'ts-loader',
				exclude: /node_modules/,
				options: {
					transpileOnly: isDevelopment,
				},
			},
		],
	},
	resolve: {
		extensions: ['.tsx', '.ts', '.jsx', '.js'],
		plugins: [new TsconfigPathsPlugin()],
	},
	devServer: {
		historyApiFallback: true,
		hot: true,
		static: {
			directory: path.join(__dirname, 'public'),
			watch: {
				ignored: '**/node_modules',
			},
		},
		compress: true,
		port: 9001,
	},
	plugins: [
		isDevelopment && new ForkTsCheckerWebpackPlugin(),
		new MiniCssExtractPlugin({
			filename: '[name].css',
		}),
	].filter(Boolean),
};
