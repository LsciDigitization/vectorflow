import type {Config} from 'jest';
import {defaults} from 'jest-config';

const config: Config = {
  verbose: true,

  moduleFileExtensions: [...defaults.moduleFileExtensions, 'mts'],

  testEnvironmentOptions: {
    url: 'http://localhost:8080'
  },

  setupFiles: ['./tests/setupTests.js'],

  // The test environment that will be used for testing
  // testEnvironment: "node",
  testEnvironment: 'jsdom',

  // The glob patterns Jest uses to detect test files
  testMatch: [
    "**/unit-tests/**/*.[jt]s?(x)",
    "**/?(*.)+(spec|test).[tj]s?(x)",
    "**/*.steps.[jt]s?(x)"
  ],

  collectCoverage: true,
  transform: {
    '^.+\\.tsx?$': ['ts-jest', {
      // useESM: true,
      // 指定ts-jest使用的tsconfig配置
      tsconfig: 'tsconfig.json'
    }],
    "\\.(ts|js)$": ['ts-jest', {
      // useESM: true,
      // 指定ts-jest使用的tsconfig配置
      tsconfig: 'tsconfig.json'
    }],
  },
  extensionsToTreatAsEsm: ['.ts'],
  roots: ['.'],
  moduleNameMapper: {
    '^@/(.*)$': `${__dirname}/src/$1`,
    '@@/(.*)$': `${__dirname}/src/.umi/$1`
  },

};

export default config;
