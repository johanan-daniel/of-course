import { useState } from 'react'
import Field from '~/components/field'
import { ButtonPrimary } from '~/components/button'
import { Link, useNavigate } from 'react-router'
import '../routes/signup.css' // Reusing the signup styles since they're appropriate for login too

interface ErrorMessage {
    email: string
    password: string
}

export function meta() {
    return [
        { title: 'Login - OfCourse' },
        { name: 'description', content: 'Login to your OfCourse account' },
    ]
}

export default function Login() {
    return (
        <div>
            <h1>Login</h1>
            <LoginBox />
        </div>
    )
}

function LoginBox() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [errorMessage, setErrorMessage] = useState<ErrorMessage | null>(null)
    const navigate = useNavigate()

    const emailHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value)
        setErrorMessage(null)
    }

    const passwordHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value)
        setErrorMessage(null)
    }

    const submitHandler = (e: React.ChangeEvent<HTMLFormElement>) => {
        e.preventDefault()

        const errors = { email: '', password: '' }

        if (email.length < 2 || email.length > 100 || !email.includes('@')) {
            errors.email = 'Invalid email address'
        }

        if (password.length < 1) {
            errors.password = 'Password is required'
        }

        if (errors.email || errors.password) {
            setErrorMessage(errors)
            return
        }

        console.log('Login attempt with:', email, password)
        // TODO: Send to server for authentication
        // TODO: Receive & save JWT
        navigate('/')
    }

    return (
        <div className="contents login">
            <div className="formContainer">
                <form onSubmit={submitHandler}>
                    <ErrorBlock errorMessage={errorMessage} />
                    <Field
                        value={email}
                        onChange={emailHandler}
                        placeholder={'Email address'}
                        autoComplete="username"
                    />
                    <Field
                        value={password}
                        onChange={passwordHandler}
                        placeholder={'Password'}
                        type="password"
                        autoComplete="current-password"
                    />
                    <div style={{ marginTop: '20px' }}>
                        <ButtonPrimary className="formSubmit" type="submit">
                            Login
                        </ButtonPrimary>
                    </div>
                    <div className="linkContainer">
                        <Link to="/signup" className="link">
                            Don't have an account? Sign up
                        </Link>
                    </div>
                </form>
            </div>
        </div>
    )
}

function ErrorBlock({ errorMessage }: { errorMessage: ErrorMessage | null }) {
    if (!errorMessage || (!errorMessage.email && !errorMessage.password)) {
        return ''
    }

    let message = ''

    if (errorMessage.email) {
        message = errorMessage.email
    } else if (errorMessage.password) {
        message = errorMessage.password
    }

    return <p className="error message">{message}</p>

    // return (
    //     <div style={{ marginBottom: '15px' }}>
    //         {errorMessage.email && (
    //             <div style={{ color: 'var(--error-red)' }}>
    //                 {errorMessage.email}
    //             </div>
    //         )}
    //         {errorMessage.password && (
    //             <div style={{ color: 'var(--error-red)' }}>
    //                 {errorMessage.password}
    //             </div>
    //         )}
    //     </div>
    // )
}
