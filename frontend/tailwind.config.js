/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'port-one': 'var(--port-one-color)',
        'port-two': 'var(--port-two-color)',
        'port-three': 'var(--port-three-color)',
        'port-four': 'var(--port-four-color)',
        'port-five': 'var(--port-five-color)',
      },
    },
  },
  plugins: [],
}